import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, map, Observable, of, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { AuthResponse } from '../interfaces/authResponse.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // private tokenSubject: BehaviorSubject<string | null>;
  // public token: Observable<string | null>;
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();
  private baseUrl = environment.baseUrl;

  // constructor(private http: HttpClient, private router: Router) {
  //   this.tokenSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));
  //   this.token = this.tokenSubject.asObservable();
  // }

  constructor(private http: HttpClient, private router: Router) {
    this.checkAuthentication();
  }


  // public get tokenValue(): string | null {
  //   return localStorage.getItem('token');
  // }

  // /**
  //  * Connexion de l'utilisateur
  //  * @param emailOrUsername 
  //  * @param password 
  //  * @returns token
  //  */
  // login(emailOrUsername: string, password: string): Observable<AuthResponse> {
  //   const requestData: LoginRequest = { emailOrUsername, password };
  //   return this.http.post<AuthResponse>(`${this.baseUrl}auth/login`, requestData).pipe(
  //     map((response: AuthResponse) => {
  //       if (response.token) {
  //         this.setToken(response.token);
  //       }
  //       return response;
  //     })
  //   );
  // }

  /**
   * Inscription de l'utilisateur
   * @param name
   * @param email
   * @param password
   * @returns Observable<AuthResponse>
   */
  register(name: string, email: string, password: string): Observable<AuthResponse> {
    const requestData: RegisterRequest = { name, email, password };
    return this.http.post<AuthResponse>(`${this.baseUrl}auth/register`, requestData, {
      withCredentials: true,
    }).pipe(
      map((response: AuthResponse) => {
        this.isAuthenticatedSubject.next(true);
        return response;
      }),
      catchError((error) => {
        this.isAuthenticatedSubject.next(false);
        return throwError(() => error);
      })
    );
  }

  /**
   * Connexion de l'utilisateur
   * @param emailOrUsername
   * @param password
   * @returns Observable<any>
   */
  login(emailOrUsername: string, password: string): Observable<any> {
    const requestData = { emailOrUsername, password };
    return this.http.post(`${this.baseUrl}auth/login`, requestData, { withCredentials: true }).pipe(
      map(() => {
        this.isAuthenticatedSubject.next(true);
      })
    );
  }

  /**
   * Déconnecte l'utilisateur
   */
  logout(): void {

    this.http.post(`${this.baseUrl}auth/logout`, {}, {
      withCredentials: true,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.isAuthenticatedSubject.next(false);
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Logout failed', error);
      }
    });
  }

  /**
   * Vérifie si l'utilisateur est connecté au chargement de l'application
   */
  private checkAuthentication(): void {
    this.http.get(`${this.baseUrl}auth/me`, { withCredentials: true })
      .pipe(
        map(() => true),
        catchError(() => of(false))
      )
      .subscribe(isAuthenticated => {
        this.isAuthenticatedSubject.next(isAuthenticated);
      });
  }

  /**
   * Méthode utilisée par le reste de l'application pour vérifier l'état d'authentification
   */
  isAuthenticated(): boolean {
    console.log('isAuthenticated', this.isAuthenticatedSubject.value);
    return this.isAuthenticatedSubject.value;
  }

  // /**
  //  * Déconnecte l'utilisateur
  //  */
  // logout(): void {
  //   this.clearToken();
  //   this.router.navigate(['/']);
  // }

  // /**
  //  * Vérifie si l'utilisateur est connecté
  //  * @returns boolean
  //  */
  // isAuthenticated(): boolean {
  //   return !!this.tokenValue;
  // }


  // /**
  //  * Enregistre le token dans le local storage
  //  * @param token 
  //  */
  // private setToken(token: string): void {
  //   localStorage.setItem('token', token);
  //   this.tokenSubject.next(token);
  // }

  // /**
  //  * Supprime le token du local storage
  //  */
  // private clearToken(): void {
  //   localStorage.removeItem('token');
  //   this.tokenSubject.next(null);
  // }


}
