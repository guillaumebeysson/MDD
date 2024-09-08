import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { User } from '../interfaces/user.interface';
import { UserProfileUpdate } from '../interfaces/userProfileUpdate.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  /**
   * S'abonne à un topic
   * @param topicId id du topic
   */
  subscribeToTopic(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}user/subscribe/${topicId}`, null);
  }

  /**
   * Se désabonne d'un topic
   * @param topicId id du topic
   */
  unsubscribeFromTopic(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}user/unsubscribe/${topicId}`);
  }

  /**
   * Récupère la liste des topics auxquels l'utilisateur est abonné
   */
  getSubscribedTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}user/subscriptions`);
  }

  /**
   * Récupère les informations de l'utilisateur
   */
  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}user`);
  }

  /**
   * Met à jour les informations de l'utilisateur
   * @param profile Données de profil à mettre à jour
   */
  updateUserProfile(profile: UserProfileUpdate): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}user`, profile);
  }


}
