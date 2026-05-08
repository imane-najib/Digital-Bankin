import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';
import { inject } from 'vitest';


export const authorizationGuard: CanActivateFn = (route, state) => {
  // @ts-ignore
  const authService:any = inject(AuthService);
  // @ts-ignore
  const router:any = inject(Router);

  if (authService.roles.includes('ADMIN')) {
    return true;
  } else {
    return router.navigateByUrl('admin/not-authorized'); // redirection
  }
};
