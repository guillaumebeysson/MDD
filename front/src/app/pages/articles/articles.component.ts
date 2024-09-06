import { Component, OnInit } from '@angular/core';
import { Article } from '../../interfaces/article.interface';
import { ArticleService } from '../../services/article.service';
import { ArticleComponent } from '../../article/article.component';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatOption, MatSelect } from '@angular/material/select';
import { Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [ArticleComponent,
    MatButton,
    MatIcon,
    MatSelect,
    MatOption,
    RouterLink
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.css'
})
export class ArticlesComponent implements OnInit {

  articles: Article[] = [];
  sortedArticles: Article[] = [];
  sortCriteria: string = 'date';  // Par défaut, on tri par date
  subscription!: Subscription;

  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.subscription = this.articleService.getSubscribedArticles().subscribe((data: Article[]) => {
      this.articles = data;
      this.sortArticles();
    });
  }

  sortArticles(): void {
    this.sortedArticles = [...this.articles];

    if (this.sortCriteria === 'date') {
      this.sortedArticles.sort((a, b) => {
        const dateA = new Date(a.createdAt).getTime();
        const dateB = new Date(b.createdAt).getTime();
        return dateB - dateA;
      });
    } else if (this.sortCriteria === 'author') {
      this.sortedArticles.sort((a, b) => {
        const nameA = a.username.toLowerCase();
        const nameB = b.username.toLowerCase();
        if (nameA < nameB) return -1;
        if (nameA > nameB) return 1;
        return 0;
      });
    } else if (this.sortCriteria === 'title') {
      this.sortedArticles.sort((a, b) => {
        const titleA = a.title.toLowerCase();
        const titleB = b.title.toLowerCase();
        if (titleA < titleB) return -1;
        if (titleA > titleB) return 1;
        return 0;
      });
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}