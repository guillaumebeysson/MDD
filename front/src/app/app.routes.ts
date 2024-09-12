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
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
    { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
    { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
    { path: 'articles', component: ArticlesComponent, canActivate: [AuthGuard] },
    { path: 'topics', component: TopicsComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'articles/create-article', component: ArticleFormComponent, canActivate: [AuthGuard] },
    { path: 'articles/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] },
    { path: 'unauthorized', component: UnauthorizedComponent },
    { path: 'notfound', component: NotfoundComponent },
    { path: '**', component: NotfoundComponent },
];
