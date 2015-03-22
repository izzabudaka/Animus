//
//  Streamer.m
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import "Streamer.h"
#define serverPort 666
#define serverIp 172.22.85.123

@interface Streamer()

@property (nonatomic, strong) NSInputStream  *inputStream;
@property (nonatomic, strong) NSOutputStream *outputStream;

@end

@implementation Streamer



- (id)init
{
    self = [super init];
    if(self) {
        [self initNetworkCommunication];
    }
    
    return self;
}

- (void)initNetworkCommunication {
    CFReadStreamRef readStream;
    CFWriteStreamRef writeStream;
    
    CFStreamCreatePairWithSocketToHost(NULL, (CFStringRef)@"172.20.10.8", 666, &readStream, &writeStream);

    self.inputStream  = (__bridge  NSInputStream *)readStream;
    self.outputStream = (__bridge  NSOutputStream *)writeStream;
    
    
    [self.inputStream setDelegate:self];
    [self.outputStream setDelegate:self];
    
    [self.inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop]
                                forMode:NSDefaultRunLoopMode];
    
    [self.outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop]
                                 forMode:NSDefaultRunLoopMode];

    [self.inputStream open];
    [self.outputStream open];
    
}

-(void)testStreamWithMessage:(NSString *)message {
    NSData *data = [[NSData alloc]
                    initWithData:[message dataUsingEncoding:NSASCIIStringEncoding]];
    
    [self.outputStream write:[data bytes]
                   maxLength:[data length]];
}

-(void)send:(NSData *)data {
    
   NSInteger x = [ self.outputStream write:[data bytes]
                                 maxLength:[data length]];
    NSLog(@"Actually Sent: %lu",(long unsigned)x);
}

-(void)stream:(NSStream *)theStream handleEvent:(NSStreamEvent)streamEvent {
    
    switch (streamEvent) {
            
        case NSStreamEventOpenCompleted:
            NSLog(@"Stream opened");
            break;
            
        case NSStreamEventHasBytesAvailable:
            break;
            
        case NSStreamEventErrorOccurred:
            NSLog(@"Can not connect to the host!");
            break;
            
        case NSStreamEventEndEncountered:
            break;
            
        default:
            NSLog(@"Unknown event");
    }
    
}

@end
