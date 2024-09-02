import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatError, MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { BackButtonComponent } from "../../back-button/back-button.component";
import { AuthService } from '../../services/auth.service';
import { Topic } from '../../interfaces/topic.interface';
import { UserService } from '../../services/user.service';
import { TopicItemComponent } from '../../topic-item/topic-item.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user',
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
    BackButtonComponent,
    TopicItemComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {

  username: string = '';
  email: string = '';
  password: string = '';
  subscribedTopics: Topic[] = [];
  private subscriptions: Subscription = new Subscription();

  constructor(private authService: AuthService, private userService: UserService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadUserProfile();
    this.loadSubscribedTopics();
  }

  removeTopic(topicId: number): void {
    this.subscribedTopics = this.subscribedTopics.filter(topic => topic.id !== topicId);
    console.log('removeTopic--------------', topicId);
  }

  logout(): void {
    this.authService.logout();
  }

  loadUserProfile(): void {
    this.subscriptions.add(
      this.userService.getCurrentUser().subscribe({
        next: (user) => {
          this.username = user.name;
          this.email = user.email;
        },
        error: (error) => {
          this.snackBar.open('Erreur lors de la récupération des informations de profil', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  loadSubscribedTopics(): void {
    this.subscriptions.add(
      this.userService.getSubscribedTopics().subscribe({
        next: (topics) => this.subscribedTopics = topics,
        error: (error) => console.error('Erreur lors de la récupération des abonnements:', error)
      })
    );
  }

  updateProfile(): void {
    this.subscriptions.add(
      this.userService.updateUserProfile({ name: this.username, email: this.email, password: this.password }).subscribe({
        next: () => {
          this.snackBar.open('Profil mis à jour avec succès', 'X', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
          this.logout();
        },
        error: (error) => {
          if (error.status === 409) {
            this.snackBar.open('Email ou nom d\'utilisateur déjà utilisé', 'X', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          } else if (error.status === 400) {
            this.snackBar.open('Données invalides. Veuillez vérifier les informations fournies.', 'X', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          } else {
            this.snackBar.open('Erreur lors de la mise à jour du profil', 'X', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

}
