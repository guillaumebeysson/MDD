import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private tokenSubject: BehaviorSubject<string | null>;
  public token: Observable<string | null>;

  constructor(private http: HttpClient) {
    this.tokenSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));
    this.token = this.tokenSubject.asObservable();
  }

  public get tokenValue(): string | null {
    return this.tokenSubject.value;
  }

  login(emailOrUsername: string, password: string) {
    return this.http.post<any>(`http://localhost:8080/api/auth/login`, { emailOrUsername, password }).pipe(
      map((response) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.tokenSubject.next(response.token);
        }
        return response;
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.tokenSubject.next(null);
  }

  register(email: string, name: string, password: string): Observable<any> {
    return this.http.post<any>(`http://localhost:8080/api/auth/register`, { email, name, password }).pipe(
      map((response) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.tokenSubject.next(response.token);
        }
        return response;
      })
    );
  }

  isAuthenticated(): boolean {
    return !!this.tokenValue;
  }





}
