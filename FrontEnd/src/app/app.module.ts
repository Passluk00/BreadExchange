import { NgModule } from '@angular/core';
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {AppRoutes} from './app.routes'
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";





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
    HttpClient,
  ]

})

export class AppModule {


}

