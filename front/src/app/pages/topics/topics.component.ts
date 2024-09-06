import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { Topic } from '../../interfaces/topic.interface';
import { TopicService } from '../../services/topic.service';
import { TopicItemComponent } from "../../topic-item/topic-item.component";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [TopicItemComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.css'
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];
  subscription!: Subscription;


  constructor(private topicService: TopicService, private snackBar: MatSnackBar) { }

  /**
   * Récupère la liste des topics
   */
  ngOnInit(): void {
    this.subscription = this.topicService.getTopics().subscribe({
      next: (data) => {
        this.topics = data;
      },
      error: (error) => {
        this.snackBar.open(error?.message || `Erreur lors de la récupération des topics`, 'X', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
      }
    })
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
