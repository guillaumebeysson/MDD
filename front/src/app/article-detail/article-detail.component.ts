import { Component, OnInit } from '@angular/core';
import { Article } from '../interfaces/article.interface';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../services/article.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { BackButtonComponent } from "../back-button/back-button.component";
import { MatError, MatFormField, MatLabel } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';


@Component({
  selector: 'app-article-detail',
  standalone: true,
  imports: [CommonModule,
    MatIcon,
    BackButtonComponent,
    MatFormField, MatLabel,
    FormsModule,
    MatError,
    MatInput,
    MatButton],
  templateUrl: './article-detail.component.html',
  styleUrl: './article-detail.component.css'
})
export class ArticleDetailComponent implements OnInit {

  article!: Article;

  constructor(private route: ActivatedRoute, private articleService: ArticleService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    if (articleId) {
      this.articleService.getArticleById(+articleId).subscribe({
        next: (data) => (this.article = data),
        error: (error) => {
          this.snackBar.open('Erreur lors de la récupération de l\'article', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      });
    }
  }
}
