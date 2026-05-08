import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {
  constructor(public authService: AuthService,private router :Router) {}//on a fait le service authentifiaction public pour qu'il soit accessible dans html de nav

  ngOnInit() {}

  handelogout() {
    this.authService.logout();//pour laisser tiut de l'authentification dans son service
  }
}
