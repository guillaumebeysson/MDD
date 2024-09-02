import { Component } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { TopicService } from '../services/topic.service';
import { ArticleService } from '../services/article.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserService } from '../services/user.service';
import { BackButtonComponent } from '../back-button/back-button.component';
import { MatError, MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatOption, MatSelect } from '@angular/material/select';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { ArticleData } from '../interfaces/articleData.interface';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [BackButtonComponent,
    MatFormField,
    MatLabel,
    MatInput,
    MatSelect,
    FormsModule,
    MatOption,
    ReactiveFormsModule,
    MatButton,
    MatError
  ],
  templateUrl: './article-form.component.html',
  styleUrl: './article-form.component.css'
})
export class ArticleFormComponent {
  title: string = '';
  content: string = '';
  selectedTopicId: number | null = null;
  topics: Topic[] = [];
  userId: number | null = null;
  private subscriptions: Subscription = new Subscription();

  constructor(
    private topicService: TopicService,
    private articleService: ArticleService,
    private snackBar: MatSnackBar,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadTopics();
    this.loadUserId();
  }

  loadTopics(): void {
    this.subscriptions.add(
      this.topicService.getTopics().subscribe({
        next: (data) => {
          this.topics = data.sort((a, b) => a.title.localeCompare(b.title));
        },
        error: (error) => {
          this.snackBar.open('Erreur lors de la récupération des thèmes.', 'X', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      })
    );
  }

  loadUserId(): void {
    this.subscriptions.add(
      this.userService.getCurrentUser().subscribe(user => {
        this.userId = user ? user.id : null;
      })
    );
  }

  onSubmit(): void {
    if (!this.userId) {
      this.snackBar.open('Utilisateur non identifié. Veuillez vous reconnecter.', 'X', {
        duration: 3000,
        panelClass: ['snackbar-error']
      });
      return;
    }

    const articleData: ArticleData = {
      title: this.title,
      content: this.content,
      userId: this.userId,
      topicId: this.selectedTopicId
    };

    this.subscriptions.add(
      this.articleService.createArticle(articleData).subscribe({
        next: () => {
          this.snackBar.open('Article créé avec succès.', 'X', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
          this.router.navigate(['/articles']);
        },
        error: (error) => {
          this.snackBar.open(error.error?.message || 'Erreur lors de la création de l\'article.', 'X', {
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