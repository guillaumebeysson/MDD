import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { delay, Observable } from 'rxjs';
import { Article } from '../interfaces/article.interface';
import { ArticleData } from '../interfaces/articleData.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.baseUrl}posts`);
  }

  getSubscribedArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.baseUrl}posts/interests`);
  }

  createArticle(articleData: ArticleData): Observable<Article> {
    return this.http.post<Article>(`${this.baseUrl}posts`, articleData);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.baseUrl}posts/${id}`).pipe(delay(1000));
  }

}
