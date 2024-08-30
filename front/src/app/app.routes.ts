import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { UserComponent } from './pages/user/user.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { ArticleFormComponent } from './article-form/article-form.component';
import { ArticleDetailComponent } from './article-detail/article-detail.component';
import { UnauthorizedComponent } from './pages/unauthorized/unauthorized.component';
import { NotfoundComponent } from './pages/notfound/notfound.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'articles', component: ArticlesComponent },
    { path: 'topics', component: TopicsComponent },
    { path: 'user', component: UserComponent },
    { path: 'articles/create-article', component: ArticleFormComponent },
    { path: 'articles/:id', component: ArticleDetailComponent },
    { path: 'unauthorized', component: UnauthorizedComponent },
    { path: 'notfound', component: NotfoundComponent },
];
