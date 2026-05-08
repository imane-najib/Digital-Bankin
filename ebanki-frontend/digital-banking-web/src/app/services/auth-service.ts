import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiurl: string = 'http://localhost:8085';
  accessToken!: string; //pour stocker jwt obtenu de backend
  username!: string; //pour stocker les user qu'on extrait de jwt apres le decoder
  roles!: string; //pour stocker les roles qu'on extrait de jwt apres le decoder
  isAuthenticated!: boolean; //

  constructor(
    private httpclient: HttpClient,
    private router: Router,
  ) {}

  public login(username: string, password: string) {
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
    };
    let params = new HttpParams().set('username', username).set('password', password);
    return this.httpclient.post(this.apiurl + '/auth/login', params, options);
  }

  loadProfile(data: any) {
    this.isAuthenticated = true;
    this.accessToken = data['acces-tocken']; //on recupere de reponse de post le token
    let decodedJwt: any = jwtDecode(this.accessToken); //on decode//n'oublie pas de installer le decoder (npm install jwt-decode)
    this.username = decodedJwt.sub; //on extrait le username
    this.roles = decodedJwt.scope; //on extrait les roles
    window.localStorage.setItem('jwt-token', this.accessToken); //le stocker dans locale storage
  }

  logout() {
    this.isAuthenticated = false;
    this.accessToken = '';
    this.username = '';
    this.roles = '';
    window.localStorage.removeItem('acces-token');
    this.router.navigateByUrl('/login');
  }

  loadJWTokenFromlocalStorage() {
    let token = window.localStorage.getItem('jwt-token'); //amener de localstorage au demarage de app (voir cette methode est appelé au démarage )
    if (token) {
      this.loadProfile({ 'acces-token': token });
      this.router.navigateByUrl('/admin/customers');
    }
  }
}
