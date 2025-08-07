// src/app/app.module.ts
import { NgModule }           from '@angular/core';
import { BrowserModule }      from '@angular/platform-browser';
import { AppRoutingModule }   from './app-routing-module';
import { AppComponent }       from './app';
import { WelcomeComponent }   from './welcome/welcome';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent        // only these two
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
