import { Component, OnDestroy } from '@angular/core';
import { FormControl, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { BackButtonComponent } from "../../back-button/back-button.component";
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    MatCardContent,
    MatFormField,
    MatLabel,
    FormsModule,
    MatInput,
    MatButton,
    MatIcon,
    ReactiveFormsModule,
    BackButtonComponent,
    MatSnackBarModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnDestroy {

  emailOrUsername: string = '';
  password: string = '';
  errorMessage: string | null = null;
  subscription!: Subscription;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar, private userService: UserService) { }

  /**
   * Connexion de l'utilisateur
   */
  onSubmit(): void {
    this.subscription = this.authService.login(this.emailOrUsername, this.password).subscribe({
      next: () => {
        this.userService.getCurrentUser().subscribe({
          next: (user) => {
            this.router.navigate(['/articles']);
            this.snackBar.open(`Content de vous voir ${user.name} !`, 'X', {
              duration: 4000,
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Erreur lors de la récupération des informations utilisateur.', 'X', {
              duration: 4000,
              panelClass: ['snackbar-error']
            });
          }
        });
      },
      error: (error) => {
        this.snackBar.open(error.error?.message || 'Une erreur s\'est produite. Veuillez réessayer.', 'X', {
          duration: 4000,
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
