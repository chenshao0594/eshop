import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CategoryComponent } from './category.component';
import { CategoryDetailComponent } from './category-detail.component';
import { CategoryPopupComponent, CategoryDialogComponent } from './category-dialog.component';
import { CategoryDeletePopupComponent } from './category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CategoryResolvePagingParams implements Resolve<any> {

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

export const categoryRoute: Routes = [
  {
    path: 'category',
    component: CategoryComponent,
    resolve: {
      'pagingParams': CategoryResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.category.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'category-new',
    component: CategoryDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.category.home.title'
    },
  }, {
    path: 'category/:id',
    component: CategoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.category.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
      path: 'category/:id/edit',
      component: CategoryDialogComponent,
      data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'eshopApp.category.home.title'
      },
      canActivate: [UserRouteAccessService],
  }
];

export const categoryPopupRoute: Routes = [
  
  {
    path: 'category/:id/delete',
    component: CategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.category.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
