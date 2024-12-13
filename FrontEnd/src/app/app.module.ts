import { NgModule } from '@angular/core';
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {LoginComponent} from "./pages/login/login.component";
import {HTTP_INTERCEPTORS, HttpClient} from "@angular/common/http";
import {AppRoutes} from './app.routes'
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";





@NgModule({
  declarations: [],

  imports: [
    BrowserModule,
    AppRoutes,
    FormsModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
  ],

  providers: [
    HttpClient

  ],
})

export class AppModule {


}

