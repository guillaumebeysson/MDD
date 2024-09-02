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

  subscribeToTopic(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}user/subscribe/${topicId}`, null);
  }

  unsubscribeFromTopic(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}user/unsubscribe/${topicId}`);
  }

  getSubscribedTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}user/subscriptions`);
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}user`);
  }

  updateUserProfile(profile: UserProfileUpdate): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}user`, profile);
  }


}
