import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { MatCard, MatCardActions, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-topic-item',
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    MatCardContent,
    MatCardActions,
    MatButton,
  ],
  providers: [
    MatSnackBar
  ],
  templateUrl: './topic-item.component.html',
  styleUrl: './topic-item.component.css'
})
export class TopicItemComponent {

  @Input() topic!: Topic;
  @Input() isSubscribed: boolean = false;
  @Output() unsubscribed: EventEmitter<number> = new EventEmitter<number>();
  subscription!: Subscription;

  constructor(private userService: UserService, private snackBar: MatSnackBar) { }

  onSubscribe() {
    {
      if (this.isSubscribed) {
        this.subscription = this.userService.unsubscribeFromTopic(this.topic.id).subscribe({
          next: () => {
            this.snackBar.open(`Désabonné du topic: ${this.topic.title}`, 'X', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });
            this.unsubscribed.emit(this.topic.id);
          },
          error: (error) => {
            this.snackBar.open(error.error?.message || `Erreur lors du désabonnement au topic: ${this.topic.title}`, 'X', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        });
      } else {
        this.subscription = this.userService.subscribeToTopic(this.topic.id).subscribe({
          next: () => {
            this.snackBar.open(`Abonné au thème: ${this.topic.title}`, 'X', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open(`Vous êtes déjà abonné à ce thème` || `Erreur lors de l'abonnement au topic: ${this.topic.title}`, 'X', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        });
      }
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
