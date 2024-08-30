import { HttpErrorResponse, HttpEvent, HttpRequest } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";
import { HttpHandlerFn } from '@angular/common/http';

export function errorInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            let errorMessage = '';
            if (error.error instanceof ErrorEvent) {
                errorMessage = `Error: ${error.error.message}`;
            } else {
                switch (error.status) {
                    case 400: // BadRequest
                        break;
                    case 401: // Unauthorized
                        localStorage.clear();
                        window.location.href = '/login';
                        break;
                    case 403: // Forbidden
                        window.location.href = '/unauthorized';

                        break;
                    case 404: // NotFound
                        window.location.href = '/notFound';
                        break;
                    default:
                        // Internal error
                        break;
                }
                errorMessage = `Error Code: ${error.status}\nMessage: ${error.error.message}`;
            }
            return throwError(errorMessage);
        })
    );
}