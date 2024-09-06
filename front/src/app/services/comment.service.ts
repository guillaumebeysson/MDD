import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  /**
   * Récupère les commentaires d'un article
   * @param articleId id de l'article
   */
  getCommentsByArticleId(articleId: number) {
    return this.http.get(`${this.baseUrl}comments/post/${articleId}`);
  }

  /**
   * Crée un commentaire
   * @param commentData données du commentaire
   */
  createComment(commentData: { content: string, postId: number }): Observable<Comment> {
    return this.http.post<Comment>(`${this.baseUrl}comments`, commentData);
  }
}
