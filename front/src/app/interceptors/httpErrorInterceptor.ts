import { HttpErrorResponse, HttpEvent, HttpRequest } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";
import { HttpHandlerFn } from '@angular/common/http';

/**
 * Intercepteur pour les erreurs HTTP
 * @param req Requête HTTP
 * @param next Fonction de rappel pour la requête suivante
 * @returns Observable<HttpEvent<unknown>>
 */
export function errorInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.error instanceof ErrorEvent) {
                console.error(`Client-side error: ${error.error.message}`);
            } else {
                switch (error.status) {
                    case 401: // Unauthorized
                        if (!req.url.includes('/auth/login') && !window.location.pathname.includes('/login')) {
                            window.location.href = '/login';
                        }
                        break;
                    case 403: // Forbidden
                        window.location.href = '/unauthorized';
                        break;
                    case 404: // NotFound
                        window.location.href = '/notFound';
                        break;
                    default:
                        break;
                }
            }
            return throwError(() => error);
        })
    );
}
