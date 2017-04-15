import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { IntegrationModuleComponent } from './integration-module.component';
import { IntegrationModuleDetailComponent } from './integration-module-detail.component';
import { IntegrationModulePopupComponent, IntegrationModuleDialogComponent } from './integration-module-dialog.component';
import { IntegrationModuleDeletePopupComponent } from './integration-module-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class IntegrationModuleResolvePagingParams implements Resolve<any> {

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

export const integrationModuleRoute: Routes = [
  {
    path: 'integration-module',
    component: IntegrationModuleComponent,
    resolve: {
      'pagingParams': IntegrationModuleResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.integrationModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'integration-module-new',
    component: IntegrationModuleDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.integrationModule.home.title'
    },
  }, {
    path: 'integration-module/:id',
    component: IntegrationModuleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.integrationModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const integrationModulePopupRoute: Routes = [
  {
    path: 'integration-module/:id/edit',
    component: IntegrationModulePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.integrationModule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'integration-module/:id/delete',
    component: IntegrationModuleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.integrationModule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
