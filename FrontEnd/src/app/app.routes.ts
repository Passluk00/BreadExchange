import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {NgModule} from "@angular/core";
import {RegisterComponent} from "./pages/register/register.component";

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
  }


];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports:[RouterModule]
})

export class AppRoutes {}
