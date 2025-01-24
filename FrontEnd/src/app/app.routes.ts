import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {NgModule} from "@angular/core";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {PageNotFoundComponent} from "./shared/page-not-found/page-not-found.component";

export const routes: Routes = [

  {
    path:"login",
    component: LoginComponent,
    title:"LogIn"

  },
  {
    path:"register",
    component: RegisterComponent,
    title:"Register"
  },
  {
    path:"activate-account",
    component: ActivateAccountComponent,
    title:"Activate Account"
  },
  {
    path:"",
    loadChildren: () => import('./shared/homepage/homepage.module').then(m =>m.HomepageModule)
  },
  {
    path:"bakery",
    loadChildren: () => import('./shared/bakery/bakery.module').then(m=>m.BakeryModule)
  },
  {
    path: '**',
    redirectTo: ""
  },
  {
    path:"404",
    component: PageNotFoundComponent,
    title:"Page Not Found"
  }



];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutes {}
