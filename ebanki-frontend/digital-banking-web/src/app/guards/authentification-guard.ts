import { CanActivateFn, Router } from '@angular/router';
import { inject } from 'vitest';
import { AuthService } from '../services/auth-service';

export const authentificationGuard: CanActivateFn = (route, state) => {
  // @ts-ignore
  const authService:any = inject(AuthService); //injecte le  service pour recupérer le authentificated car s'il a passé par login ce boolen sera rempli
  // @ts-ignore
  const router:any = inject(Router);
  if (authService.isAuthenticated) {
    //cad il a passé par login
    return true;
  } else {
    return router.navigateByUrl('/login'); //redireger vers login
  }
};
