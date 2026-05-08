import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from 'vitest';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth-service';


export const appHttpInterceptor: HttpInterceptorFn = (req, next) => {
  // @ts-ignore
  const authService = inject(AuthService); //on a injecter le service auth prendre jwt pour les ajouter dans les requetes

  // @ts-ignore
  const token = authService.accessToken; //le jwt

  console.log(' ****** ');
  console.log(req.url);

  // on doit pas ajouter le tocken dans l'url de login car c'est elle qui donne jwt
  if (!req.url.includes('/auth/login')) {
    // @ts-ignore
    const token = authService.accessToken; //tocken de service qui nous la donner apres login

    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    //return next(clonedReq);//avant d'utilser localstorage

    return next(clonedReq).pipe(
      //si la requete deja contient un erreur cad en travaillons son tocken a expire
      catchError((err) => {
        //  si token expiré ou invalide
        if (err.status === 401) {
          // @ts-ignore
          authService.logout(); //
        }

        return throwError(() => err);
      }),
    );
  }

  return next(req);
};
