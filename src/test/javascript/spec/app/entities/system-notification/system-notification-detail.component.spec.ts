import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SystemNotificationDetailComponent } from '../../../../../../main/webapp/app/entities/system-notification/system-notification-detail.component';
import { SystemNotificationService } from '../../../../../../main/webapp/app/entities/system-notification/system-notification.service';
import { SystemNotification } from '../../../../../../main/webapp/app/entities/system-notification/system-notification.model';

describe('Component Tests', () => {

    describe('SystemNotification Management Detail Component', () => {
        let comp: SystemNotificationDetailComponent;
        let fixture: ComponentFixture<SystemNotificationDetailComponent>;
        let service: SystemNotificationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [SystemNotificationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SystemNotificationService,
                    EventManager
                ]
            }).overrideComponent(SystemNotificationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SystemNotificationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemNotificationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SystemNotification(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.systemNotification).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
