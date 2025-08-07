import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom }      from '@angular/core';
import { AppComponent }            from './app/app';                   // matches app.ts
import { BrowserModule }           from '@angular/platform-browser';
import { AppRoutingModule }        from './app/app-routing-module';     // matches app-routing-module.ts
import { FormsModule }             from '@angular/forms';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      BrowserModule,
      AppRoutingModule,
      FormsModule
    )
  ]
})
.catch(err => console.error(err));
