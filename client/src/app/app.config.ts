import {
  ApplicationConfig,
  provideZoneChangeDetection,
  APP_INITIALIZER,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { routes } from './app.routes';
import {
  HTTP_INTERCEPTORS,
  provideHttpClient,
  withFetch,
  withInterceptorsFromDi,
  withXsrfConfiguration,
} from '@angular/common/http';
import { KeycloakBearerInterceptor, KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environment';

export const initializeKeycloak = (keycloak: KeycloakService) => {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloakServer,
        realm: environment.keycloakRealm,
        clientId: environment.keycloakClientId,
      },

      shouldAddToken: (request) => {
        const { method, url } = request;
        const isGetRequest = 'GET' === method.toUpperCase();
        const acceptablePaths = ['/assets', '/clients/public'];
        const isAcceptablePathMatch = acceptablePaths.some((path) =>
          url.includes(path)
        );

        return !(isGetRequest && isAcceptablePathMatch);
      },

      shouldUpdateToken(request) {
        return !request.headers.get('token-update') === false;
      },
      bearerExcludedUrls: ['https://pixabay.com'],
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideAnimationsAsync(),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true,
    },
    KeycloakService,
    provideHttpClient(withInterceptorsFromDi()), // serve per keycloak, non e' chiaro dalla documentazione!
    // provideHttpClient(),
  ],
};

// logo per piattaforma di tracciabilità di filiera agroalimentare che usa la blockchain e i sensori iot
