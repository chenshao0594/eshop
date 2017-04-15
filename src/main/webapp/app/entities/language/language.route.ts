import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LanguageComponent } from './language.component';
import { LanguageDetailComponent } from './language-detail.component';
import { LanguagePopupComponent, LanguageDialogComponent } from './language-dialog.component';
import { LanguageDeletePopupComponent } from './language-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class LanguageResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const languageRoute: Routes = [
  {
    path: 'language',
    component: LanguageComponent,
    resolve: {
      'pagingParams': LanguageResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.language.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'language-new',
    component: LanguageDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.language.home.title'
    },
  }, {
    path: 'language/:id',
    component: LanguageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.language.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const languagePopupRoute: Routes = [
  {
    path: 'language/:id/edit',
    component: LanguagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.language.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'language/:id/delete',
    component: LanguageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.language.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
