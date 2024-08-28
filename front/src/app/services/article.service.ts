import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Article } from '../interfaces/article.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.baseUrl}posts`);
  }

  createArticle(articleData: { title: string, content: string, userId: number, topicId: number | null }): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}posts`, articleData);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.baseUrl}posts/${id}`);
  }

}
