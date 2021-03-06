import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplatePopupComponent, TemplateDialogComponent } from './template-dialog.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TemplateResolvePagingParams implements Resolve<any> {

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

export const templateRoute: Routes = [
  {
    path: 'template',
    component: TemplateComponent,
    resolve: {
      'pagingParams': TemplateResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'template/:id',
    component: TemplateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'template-new',
    component: TemplateDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'template/:id/edit',
    component: TemplateDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.template.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const templatePopupRoute: Routes = [
  
  
  {
    path: 'template/:id/delete',
    component: TemplateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.template.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
