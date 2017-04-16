import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { EmailSetting } from './email-setting.model';
import { EmailSettingPopupService } from './email-setting-popup.service';
import { EmailSettingService } from './email-setting.service';

@Component({
    selector: 'jhi-email-setting-delete-dialog',
    templateUrl: './email-setting-delete-dialog.component.html'
})
export class EmailSettingDeleteDialogComponent {

    emailSetting: EmailSetting;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private emailSettingService: EmailSettingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['emailSetting', 'sMTPSecurityEnum']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailSettingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emailSettingListModification',
                content: 'Deleted an emailSetting'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-setting-delete-popup',
    template: ''
})
export class EmailSettingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailSettingPopupService: EmailSettingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.emailSettingPopupService
                .open(EmailSettingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
