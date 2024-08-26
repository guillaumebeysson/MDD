import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../../interfaces/topic.interface';
import { TopicService } from '../../services/topic.service';
import { TopicItemComponent } from "../../topic-item/topic-item.component";

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [TopicItemComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.css'
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];

  constructor(private topicService: TopicService) { }

  ngOnInit(): void {
    this.topicService.getTopics().subscribe({
      next: (data) => {
        this.topics = data;
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

}
