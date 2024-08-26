import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatFormField, MatFormFieldControl, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { BackButtonComponent } from "../../back-button/back-button.component";
import { AuthService } from '../../services/auth.service';
import { Topic } from '../../interfaces/topic.interface';
import { UserService } from '../../services/user.service';
import { TopicItemComponent } from '../../topic-item/topic-item.component';

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

  constructor(private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getSubscribedTopics().subscribe({
      next: (topics) => this.subscribedTopics = topics,
      error: (error) => console.error('Erreur lors de la récupération des abonnements:', error)
    })
  }

  removeTopic(topicId: number): void {
    this.subscribedTopics = this.subscribedTopics.filter(topic => topic.id !== topicId);
    console.log('removeTopic--------------', topicId);
  }

  logout(): void {
    this.authService.logout();
  }

}
