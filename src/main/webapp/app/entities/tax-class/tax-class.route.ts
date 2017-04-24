import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaxClassComponent } from './tax-class.component';
import { TaxClassDetailComponent } from './tax-class-detail.component';
import { TaxClassPopupComponent, TaxClassDialogComponent } from './tax-class-dialog.component';
import { TaxClassDeletePopupComponent } from './tax-class-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TaxClassResolvePagingParams implements Resolve<any> {

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

export const taxClassRoute: Routes = [
  {
    path: 'tax-class',
    component: TaxClassComponent,
    resolve: {
      'pagingParams': TaxClassResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'tax-class/:id',
    component: TaxClassDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'tax-class-new',
    component: TaxClassDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'tax-class/:id/edit',
    component: TaxClassDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taxClassPopupRoute: Routes = [
  
  
  {
    path: 'tax-class/:id/delete',
    component: TaxClassDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.taxClass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
