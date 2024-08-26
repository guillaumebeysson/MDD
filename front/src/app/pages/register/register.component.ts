import { Component } from '@angular/core';
import { FormControl, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
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
      next: (data) => {
        this.router.navigate(['/articles']);
        this.snackBar.open('Inscription réussie, vous êtes connecté !', 'X', {
          duration: 4000,
          panelClass: ['snackbar-success']
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

}
