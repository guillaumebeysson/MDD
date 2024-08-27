import { Component, Input } from '@angular/core';
import { Article } from '../interfaces/article.interface';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    CommonModule
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.css'
})
export class ArticleComponent {

  @Input() article!: Article;

}
