import { Component, Input } from '@angular/core';
import { Article } from '../interfaces/article.interface';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    CommonModule,
    MatIcon
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.css'
})
export class ArticleComponent {

  @Input() article!: Article;

  constructor(private router: Router) { }

  /**
   * Redirige vers la page de l'article
   */
  onSelect(): void {
    this.router.navigate([`/articles/${this.article.id}`]);
  }

}
