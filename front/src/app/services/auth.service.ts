import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private tokenSubject: BehaviorSubject<string | null>;
  public token: Observable<string | null>;
  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient, private router: Router) {
    this.tokenSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));
    this.token = this.tokenSubject.asObservable();
  }

  public get tokenValue(): string | null {
    return this.tokenSubject.value;
  }

  login(emailOrUsername: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}auth/login`, { emailOrUsername, password }).pipe(
      map((response) => {
        if (response.token) {
          this.setToken(response.token);
        }
        return response;
      })
    );
  }

  logout(): void {
    this.clearToken();
    this.router.navigate(['/']);
  }

  register(name: string, email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}auth/register`, { name, email, password }).pipe(
      map((response) => {
        if (response.token) {
          this.setToken(response.token);
        }
        return response;
      })
    );
  }

  isAuthenticated(): boolean {
    return !!this.tokenValue;
  }

  private setToken(token: string): void {
    localStorage.setItem('token', token);
    this.tokenSubject.next(token);
  }

  private clearToken(): void {
    localStorage.removeItem('token');
    this.tokenSubject.next(null);
  }


}
