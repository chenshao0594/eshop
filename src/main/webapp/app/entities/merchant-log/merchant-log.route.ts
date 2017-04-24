import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MerchantLogComponent } from './merchant-log.component';
import { MerchantLogDetailComponent } from './merchant-log-detail.component';
import { MerchantLogPopupComponent, MerchantLogDialogComponent } from './merchant-log-dialog.component';
import { MerchantLogDeletePopupComponent } from './merchant-log-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MerchantLogResolvePagingParams implements Resolve<any> {

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

export const merchantLogRoute: Routes = [
  {
    path: 'merchant-log',
    component: MerchantLogComponent,
    resolve: {
      'pagingParams': MerchantLogResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'merchant-log/:id',
    component: MerchantLogDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'merchant-log-new',
    component: MerchantLogDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'merchant-log/:id/edit',
    component: MerchantLogDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const merchantLogPopupRoute: Routes = [
  
  
  {
    path: 'merchant-log/:id/delete',
    component: MerchantLogDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
