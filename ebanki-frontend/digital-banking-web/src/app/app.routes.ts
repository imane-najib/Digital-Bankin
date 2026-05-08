
import { Routes } from '@angular/router';
import { Customers } from './customers/customers';
import { Accounts } from './accounts/accounts';
import { NewCustomer } from './new-customer/new-customer';
import { Login } from './login/login';
import { AdminTemplate } from './admin-template/admin-template';
import { authentificationGuard } from './guards/authentification-guard';
import { authorizationGuard } from './guards/authorization-guard';
import { NotAuthorized } from './not-authorized/not-authorized';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  //pathMatch:a deux valeur :full ou prefix
  //full cad le path correspend exacte
  //prefix :correspondance partielle (début de l’URL)(cad les urll qui commencent par "" seront redirecté vers login)



  {
    path: 'admin',
    component: AdminTemplate,
    canActivate: [authentificationGuard], //ici on active guard d'authentification (tous ces routes necessite de passé par le login )
    children: [
      //des routes imbriquées (sous-routes) à l’intérieur d’une route principale.
      //cad :
      // /admin/Customers  /admin/Accounts  /admin/NewCustomer
      { path: 'customers', component: Customers },
      { path: 'accounts', component: Accounts },
      {
        path: 'new-customer',
        component: NewCustomer,
        canActivate: [authorizationGuard], //proteger la route avec ce guards
        data: { role: 'ADMIN' },
      },
      { path: 'not-authorized', component: NotAuthorized },
    ],
  },
];
