import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatError, MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { BackButtonComponent } from "../../back-button/back-button.component";
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [MatCard,
    MatCardTitle,
    MatCardContent,
    MatFormField,
    MatLabel,
    FormsModule,
    MatInput,
    MatButton,
    MatIcon,
    MatError,
    ReactiveFormsModule,
    BackButtonComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  username: string = '';
  email: string = '';
  password: string = '';
  subscription!: Subscription;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) { }

  onSubmit(): void {
    this.subscription = this.authService.register(this.username, this.email, this.password).subscribe({
      next: () => {
        this.router.navigate(['/articles']);
        this.snackBar.open('Inscription réussie, vous êtes connecté !', 'X', {
          duration: 4000,
          panelClass: ['snackbar-success']
        });
      },
      error: (error) => {
        if (error.status === 409) {
          if (error.error?.message.includes('User with email')) {
            this.snackBar.open('Un utilisateur avec cet email existe déjà.', 'X', {
              duration: 4000,
              panelClass: ['snackbar-error']
            });
          } else if (error.error?.message.includes('User with name')) {
            this.snackBar.open('Un utilisateur avec ce nom existe déjà.', 'X', {
              duration: 4000,
              panelClass: ['snackbar-error']
            });
          }
        } else {
          this.snackBar.open(error.error?.message || 'Une erreur s\'est produite. Veuillez réessayer.', 'X', {
            duration: 4000,
            panelClass: ['snackbar-error']
          });
        }
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
