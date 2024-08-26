import { Component, Input } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { MatCard, MatCardActions, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-topic-item',
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    MatCardContent,
    MatCardActions,
    MatButton
  ],
  templateUrl: './topic-item.component.html',
  styleUrl: './topic-item.component.css'
})
export class TopicItemComponent {

  @Input() topic!: Topic;

  onSubscribe() {
    console.log("Abonné au topic: " + this.topic.title);
  }

}
