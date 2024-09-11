import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../environments/environment.development';


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


const baseUrl = environment.baseUrl;

/**
 * Intercepteur pour ajouter withCredentials à toutes les requêtes HTTP sauf certaines (login, register)
 * @param req Requête HTTP
 * @param next Fonction de rappel pour la requête suivante
 * @returns Observable<HttpEvent<unknown>>
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const excludedUrls = [`${baseUrl}/auth/login`, `${baseUrl}/auth/register`];
  const isExcluded = excludedUrls.some(url => req.url === url);

  if (!isExcluded) {
    req = req.clone({
      withCredentials: true,
    });
  }

  return next(req);
};

