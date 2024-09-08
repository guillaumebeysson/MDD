import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

/**
 * Intercepteur pour ajouter le token JWT aux requêtes HTTP
 * @param req Requête HTTP
 * @param next Fonction de rappel pour la requête suivante
 * @returns Observable<HttpEvent<unknown>>
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  if (authService.isAuthenticated()) {
    const token = authService.tokenValue;
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
