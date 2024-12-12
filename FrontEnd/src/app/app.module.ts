import { NgModule } from '@angular/core';
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {LoginComponent} from "./pages/login/login.component";
import {HTTP_INTERCEPTORS, HttpClient} from "@angular/common/http";
import {AppRoutes} from './app.routes'





@NgModule({
  declarations: [],

  imports: [
    BrowserModule,
    AppRoutes,
    FormsModule,
    LoginComponent,

  ],

  providers: [
    HttpClient

  ],
})

export class AppModule {


}

