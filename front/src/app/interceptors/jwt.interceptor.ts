import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

// /**
//  * Intercepteur pour ajouter le token JWT aux requêtes HTTP
//  * @param req Requête HTTP
//  * @param next Fonction de rappel pour la requête suivante
//  * @returns Observable<HttpEvent<unknown>>
//  */
// export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
//   const authService = inject(AuthService);

//   if (authService.isAuthenticated()) {
//     const token = authService.tokenValue;
//     req = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//   }

//   return next(req);
// };

/**
 * Intercepteur pour ajouter withCredentials à toutes les requêtes HTTP
 * @param req Requête HTTP
 * @param next Fonction de rappel pour la requête suivante
 * @returns Observable<HttpEvent<unknown>>
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  // Ajouter withCredentials à toutes les requêtes pour envoyer les cookies avec les requêtes
  req = req.clone({
    withCredentials: true,
  });

  return next(req);
};
