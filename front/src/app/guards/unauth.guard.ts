// unauth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root',
})
export class UnauthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) { }

    canActivate(): Observable<boolean> {
        return this.authService.isAuthenticated$.pipe(
            map(isAuthenticated => {
                if (!isAuthenticated) {
                    return true;
                } else {
                    this.router.navigate(['/']);
                    return false;
                }
            }),
            catchError(() => {
                return of(true);
            })
        );
    }
}
