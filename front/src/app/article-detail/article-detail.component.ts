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
import { CommentService } from '../services/comment.service';
import { Comment } from '../interfaces/comment.interface';
import { UserService } from '../services/user.service';
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';
import { CommentData } from '../interfaces/commentData.interface';
import { Subscription } from 'rxjs';


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
    MatButton,
    LoadingSpinnerComponent],
  templateUrl: './article-detail.component.html',
  styleUrl: './article-detail.component.css'
})
export class ArticleDetailComponent implements OnInit {

  article!: Article;
  comments: Comment[] = [];
  content: string = '';
  userId: number | null = null;
  private subscriptions: Subscription = new Subscription();

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private commentService: CommentService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    if (articleId) {
      this.loadArticleDetails(+articleId);
      this.loadComments(+articleId);
      this.loadUserId();
    }
  }

  loadArticleDetails(articleId: number): void {
    this.subscriptions.add(
      this.articleService.getArticleById(articleId).subscribe({
        next: (data) => this.article = data,
        error: () => {
          this.snackBar.open('Erreur lors du chargement de l\'article', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  loadComments(articleId: number): void {
    this.subscriptions.add(
      this.commentService.getCommentsByArticleId(articleId).subscribe({
        next: (data: Object) => this.comments = data as Comment[],
        error: () => {
          this.snackBar.open('Erreur lors du chargement des commentaires', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  loadUserId(): void {
    this.subscriptions.add(
      this.userService.getCurrentUser().subscribe({
        next: (user) => {
          this.userId = user.id;
        },
        error: () => {
          this.snackBar.open('Erreur lors de la récupération de l\'utilisateur actuel', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  onSubmit(): void {
    if (!this.content.trim()) {
      this.snackBar.open('Veuillez ajouter du contenu pour envoyer un commentaire', 'X', {
        duration: 3000,
        panelClass: ['snackbar-error']
      });
      return;
    }

    const commentData: CommentData = {
      content: this.content,
      postId: this.article.id,
      userId: this.userId
    };

    this.subscriptions.add(
      this.commentService.createComment(commentData).subscribe({
        next: (createdComment) => {
          this.comments.push(createdComment);
          this.content = "";
          this.snackBar.open('Commentaire ajouté avec succès', 'X', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
        },
        error: () => {
          this.snackBar.open('Erreur lors de l\'ajout du commentaire', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
