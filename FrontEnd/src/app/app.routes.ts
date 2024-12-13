import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {NgModule} from "@angular/core";
import {RegisterComponent} from "./pages/register/register.component";
import {HomepageComponent} from "./shared/homepage/homepage.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";

export const routes: Routes = [

  {
    path:"",
    component: HomepageComponent,
    title: 'Home',
  },

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
  }


];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutes {}
