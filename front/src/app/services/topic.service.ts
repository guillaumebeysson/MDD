import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  /**
   * Récupère la liste des topics
   */
  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}topics`);
  }

}
