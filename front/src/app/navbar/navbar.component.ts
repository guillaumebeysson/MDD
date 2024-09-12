import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenav } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArticlesComponent } from '../pages/articles/articles.component';
import { RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIcon,
    MatButtonModule,
    MatSidenav,
    MatListModule,
    MatMenuModule,
    ArticlesComponent,
    RouterModule,
    AsyncPipe
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  isAuthenticated = false;

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
    // Abonnement à l'état d'authentification
    this.authService.isAuthenticated$.subscribe(
      (isAuthenticated) => {
        this.isAuthenticated = isAuthenticated;
      }
    );
  }


}
